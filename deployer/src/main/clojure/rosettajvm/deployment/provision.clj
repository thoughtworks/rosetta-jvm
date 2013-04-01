(ns rosettajvm.deployment.provision
  (:require [pallet.api :as api]
            [pallet.configure :as configure])
  (:use [pallet.crate.automated-admin-user         :only [with-automated-admin-user]]
        [rosettajvm.deployment.crate.openjdk-7-jre :only [with-openjdk-7-jre]]
        [rosettajvm.deployment.crate.rosetta-jvm   :only [with-rosetta-jvm]])
  (:gen-class :name "rosettajvm.deployment.Provisioner"))

(def ubuntu-node
  (api/node-spec
    :image {:os-family :ubuntu
            :image-id "us-east-1/ami-0cdf4965"}
    :hardware {:hardware-id "t1.micro"}
    :network {:inbound-ports [22 8000]}))

(def application-nodes
  (api/group-spec "rosetta-jvm"
    :extends [with-automated-admin-user]
              ;;with-openjdk-7-jre
              ;;with-rosetta-jvm]
    :node-spec ubuntu-node))

(defn bring-node-up-on [provider]
  (deref (api/converge
    (merge application-nodes {:count 1})
    :compute (configure/compute-service provider))))

(defn bring-node-down-on [provider]
  (deref (api/converge
    (merge application-nodes {:count 0})
    :compute (configure/compute-service provider))))

(defn -main [& args]
  (let [action (first args)
        provider (keyword (second args))]
    (case action
      "up" (bring-node-up-on provider)
      "down" (bring-node-down-on provider))))
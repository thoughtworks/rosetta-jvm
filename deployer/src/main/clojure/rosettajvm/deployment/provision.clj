(ns rosettajvm.deployment.provision
  (:require [pallet.api :as api]
            [pallet.configure :as configure])
  (:use [pallet.crate.automated-admin-user :only [automated-admin-user]]
        [rosettajvm.deployment.crate.openjdk-7-jre :only [with-openjdk-7-jre]])
  (:gen-class :name "rosettajvm.deployment.Provisioner"))

(def ubuntu-node
  (api/node-spec
    :image {:os-family :ubuntu
            :image-id "us-east-1/ami-de0d9eb7"}
    :network {:inbound-ports [22]}))

(def application-nodes
  (api/group-spec "rosetta-jvm"
    :extends [with-openjdk-7-jre]
    :phases {:bootstrap (api/plan-fn
                          (automated-admin-user))}
    :node-spec ubuntu-node))

(defn bring-node-up-on [provider]
  (api/converge
    (merge application-nodes {:count 1})
    :compute (configure/compute-service provider)))

(defn bring-node-down-on [provider]
  (api/converge
    (merge application-nodes {:count 0})
    :compute (configure/compute-service provider)))

(defn -main [& args]
  (let [action (first args)
        provider (keyword (second args))]
    (case action
      "up" (bring-node-up-on provider)
      "down" (bring-node-down-on provider))))
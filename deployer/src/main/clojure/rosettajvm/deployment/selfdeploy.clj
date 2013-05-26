(ns rosettajvm.deployment.selfdeploy
  (:require [environ.core :as environ]
            [pallet.api :as api]
            [pallet.configure :as configure])
  (:use [pallet.crate.automated-admin-user :only [with-automated-admin-user]]
        [rosettajvm.deployment.crate.etc-default :only [with-etc-default]]
        [rosettajvm.deployment.crate.openjdk-7-jre :only [with-openjdk-7-jre]]
        [rosettajvm.deployment.crate.deployer :only [with-deployer]])
  (:gen-class :name "rosettajvm.deployment.SelfDeploy"))

(def ubuntu-node
  (api/node-spec
    :image {:os-family :ubuntu
            :image-id "us-east-1/ami-0cdf4965"}
    :hardware {:hardware-id "t1.micro"}
    :network {:inbound-ports [22 8000]}))

(def application-nodes
  (api/group-spec "rosetta-jvm-deployer"
    :extends [with-automated-admin-user
              with-openjdk-7-jre
              with-deployer
              with-etc-default]
    :node-spec ubuntu-node))

(defn compute-provider []
  (pallet.compute/instantiate-provider
    "aws-ec2"
    :identity (environ/env :pallet-aws-identity)
    :credential (environ/env :pallet-aws-credential)))

(defn bring-node-up []
  (deref (api/converge
           (merge application-nodes {:count 1})
           :compute (compute-provider)
           :phase [:configure :install ]
           :async true)))

(defn bring-node-down []
  (deref (api/converge
           (merge application-nodes {:count 0})
           :compute (compute-provider)
           :phase [:configure :install ]
           :async true)))

(defn -main [& args]
  (let [action (first args)
        provider (keyword (second args))]
    (case action
      "up" (bring-node-up)
      "down" (bring-node-down))))
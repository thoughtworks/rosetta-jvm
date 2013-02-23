(ns rosettajvm.deployment.provision
  (:require pallet.node
            pallet.core
            pallet.compute
            pallet.configure))

(defn start-node []
  (pallet.core/converge
    (pallet.core/group-spec "rosetta-jvm"

      :count 1
      :node-spec (pallet.core/node-spec
                   :image {
                            :os-family :ubuntu
                            :image-id "us-east-1/ami-3c994355"}))
    :compute (pallet.configure/compute-service :aws)))
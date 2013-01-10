(ns rosettajvm.deploy
  (:require pallet.node
            pallet.core
            pallet.compute
            pallet.configure))

;(pallet.configure/defpallet
;  :services {:aws {:provider :aws-ec2,
;                   :identity "AKIAIDD67L7PPORJO5VA",
;                   :credential "PbxKOyjP72x76iuySzxkwFwGDnIvidU4fltb9mv8"}})

(defn -main [& args]
  (pallet.core/converge
    (pallet.core/group-spec "rosetta-jvm"
      :count 1
      :node-spec (pallet.core/node-spec
                   :image {:os-family :ubuntu :image-id "us-east-1/ami-9878c0f1"}))
    :compute (pallet.configure/compute-service :aws)))

(println (sort (ns-publics 'pallet.compute)))
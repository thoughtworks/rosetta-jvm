(ns rosettajvm.deployment.crate.rosetta-jvm
  (:use [pallet.api :only [plan-fn server-spec]]
        [pallet.actions :only [remote-file]]))

(def with-rosetta-jvm
  (server-spec
    :phases {:configure
             (plan-fn
               (remote-file
                 "artifacts/service.zip"
                 :blobstore :s3
                 :blob {:container "rosetta-jvm-artifacts"
                        :path "8cc9886bc468fdd53171f432af01eb7ea34ad9cb/service.zip"}))}))
(ns rosettajvm.deployment.crate.rosetta-jvm
  (:require [pallet.configure :as configure]
            [clojure.tools.logging :as logging])
  (:use [pallet.api :only [plan-fn server-spec]]
        [pallet.crate :only [defplan]]
        [pallet.actions :only [remote-file remote-directory directory exec-checked-script install-deb]]))

(defplan fetch-service-artifact
  "Fetch the RosettaJVM service artifact for the provided commit SHA"
  [commit-sha]
  (let [package-name "rosetta-jvm"
        artifact-directory "artifacts"
        artifact-name (str package-name "_" commit-sha "_all.deb")
        artifact-path (str artifact-directory "/" artifact-name)]
    (logging/debugf "Fetching RosettaJVM service artifact with SHA %s" commit-sha)
    (directory artifact-directory)
    (install-deb
      package-name
      :blobstore (configure/blobstore-service-from-config-file :aws {})
      :blob {:container "rosetta-jvm-artifacts"
             :path (str commit-sha "/" artifact-name)})))

(def with-rosetta-jvm
  (server-spec
    :phases {:install (plan-fn
                        (fetch-service-artifact "e9758ae3923669e22033b19e471e0bf8690583b2"))}))
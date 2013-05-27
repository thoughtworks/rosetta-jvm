(ns rosettajvm.deployment.crate.deployer
  (:require [pallet.configure :as configure]
            [pallet.api :as api]
            [pallet.crate :as crate]
            [pallet.actions :as actions]
            [clojure.tools.logging :as logging]))

(crate/defplan fetch-deployer-artifact
  "Fetch the RosettaJVM Deployer artifact for the provided commit SHA"
  [commit-sha]
  (let [package-name "deployer"
        artifact-directory "artifacts"
        artifact-name (str package-name "_" commit-sha "_all.deb")
        artifact-path (str artifact-directory "/" artifact-name)]
    (logging/debugf "Fetching RosettaJVM Deployer artifact with SHA %s" commit-sha)
    (actions/directory artifact-directory)
    (actions/install-deb
      package-name
      :blobstore (configure/blobstore-service-from-config-file :aws {})
      :blob {:container "rosetta-jvm-artifacts"
             :path (str commit-sha "/" artifact-name)})))

(defn with-deployer-for [commit-sha]
  (api/server-spec
    :phases {:install (api/plan-fn
                        (fetch-deployer-artifact commit-sha))}))
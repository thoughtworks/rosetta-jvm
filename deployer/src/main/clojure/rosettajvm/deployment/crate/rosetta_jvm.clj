(ns rosettajvm.deployment.crate.rosetta-jvm
  (:require [pallet.configure :as configure])
  (:use [pallet.api :only [plan-fn server-spec]]
        [pallet.crate :only [defplan]]
        [pallet.actions :only [remote-file remote-directory directory exec-checked-script]]))

(defplan fetch-service-artifact
  "Fetch the RosettaJVM service artifact for the provided commit SHA"
  [commit-sha]
  (clojure.tools.logging/debugf "Fetching RosettaJVM service artifact with SHA %s" commit-sha)
  (directory "artifacts")
  (remote-file
    "artifacts/service.tar"
    :blobstore (configure/blobstore-service-from-config-file :aws {})
    :blob {:container "rosetta-jvm-artifacts"
           :path (str commit-sha "/service.tar")})
  (remote-directory
    "service"
    :remote-file "artifacts/service.tar"
    :unpack :tar
    :tar-options "x")
  (exec-checked-script "start-rosetta-jvm"
    ("./service/bin/service" "--port" "8000" "&")))

(def with-rosetta-jvm
  (server-spec
    :phases {:configure (plan-fn (fetch-service-artifact "69a1a45e2b707239ab1b987e27a27c07b6509465"))}))
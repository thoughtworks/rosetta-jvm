(ns rosettajvm.deployment.crate.etc-default
  (:require [environ.core :as environ]
            [pallet.configure :as configure]
            [pallet.api :as api]
            [pallet.crate :as crate]
            [pallet.actions :as actions]
            [clojure.tools.logging :as logging]))

(crate/defplan create-etc-default-file
  "Push AWS keys from the local machine to remote instances"
  []
  (let [aws-identity (environ/env :pallet-aws-identity)
        aws-credential (environ/env :pallet-aws-credential)]
    (actions/remote-file
      "/etc/default/deployer"
      :content (str
                 "PALLET_AWS_IDENTITY=" aws-identity "\n"
                 "PALLET_AWS_CREDENTIAL=" aws-credential "\n"))))

(def with-etc-default
  (api/server-spec
    :phases {:configure (api/plan-fn
                          (create-etc-default-file))}))
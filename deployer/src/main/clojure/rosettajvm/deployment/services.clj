(ns rosettajvm.deployment.services
  (:require [environ.core :as environ]
            [pallet.blobstore :as blobstore]
            [pallet.compute :as compute]))

(defn aws-authentication-options []
  [:identity (environ/env :pallet-aws-identity)
   :credential (environ/env :pallet-aws-credential)])

(defn compute-service []
  (apply compute/instantiate-provider (flatten ["aws-ec2" (aws-authentication-options)])))

(defn blobstore-service []
  (apply blobstore/service (flatten ["aws-s3" (aws-authentication-options)])))
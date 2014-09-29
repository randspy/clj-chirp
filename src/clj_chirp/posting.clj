(ns clj-chirp.posting
  (:require [clj-chirp.find :as find]))

(defn- compute-new-user-element [post timestamp]
  (assoc {} :user-name (:user-name post)
            :posts-values [(assoc {} :content (:content post)
                              :timestamp timestamp)]))

(defn- add-post-to-user-element [user post timestamp]
  (assoc user :posts-values (conj (:posts-values user)
                         {:content (:content post) :timestamp timestamp})))

(defn- possition-of-existing-post-for-replacement [posts user]
  (.indexOf posts user))

(defn post [all-users-posts new-post timestamp]
  (let [user (find/find-by-user-name all-users-posts (:user-name new-post))]
    (if user
      (assoc all-users-posts (possition-of-existing-post-for-replacement all-users-posts user)
                   (add-post-to-user-element user new-post timestamp))
      (conj all-users-posts (compute-new-user-element new-post timestamp)))))

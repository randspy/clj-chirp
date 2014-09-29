(ns clj-chirp.following
  (:require [clj-chirp.find :as find]))

(defn- add-follow-element [user followed-user]
  (if (not= (:user-name user) (:user-name followed-user))
    (let [distinct-follows (set (conj (:follows user) (:user-name followed-user)))]
      (assoc user :follows distinct-follows))
    user))

(defn- possition-of-existing-post-for-replacement [posts user]
  (.indexOf posts user))

(defn follows [all-users-posts new-follow-post]
  (let [user (find/find-by-user-name all-users-posts (:user-name new-follow-post))
        followed-user (find/find-by-user-name all-users-posts (:content new-follow-post))]
    (if (and user followed-user)
      (assoc all-users-posts (possition-of-existing-post-for-replacement all-users-posts user)
                            (add-follow-element user followed-user))
      all-users-posts)))
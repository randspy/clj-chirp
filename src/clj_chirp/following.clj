(ns clj-chirp.following
  (:require [clj-chirp.find :as find]))

(defn- add-follow-element [user followed-user]
  (let [distinct-follows (vec (set (conj (:follows user) (:user-name followed-user))))
        follows-without-user (filter #(not= % (:user-name user)) distinct-follows)]
    (if (seq follows-without-user)
      (assoc user :follows follows-without-user)
      user)))

(defn- possition-of-existing-post-for-replacement [posts user]
  (.indexOf posts user))

(defn follows [existing-posts new-post]
  (let [user (find/find-by-user-name existing-posts (:user-name new-post))
        followed-user (find/find-by-user-name existing-posts (:content new-post))]
    (if (and user followed-user)
      (assoc existing-posts (possition-of-existing-post-for-replacement existing-posts user)
                            (add-follow-element user followed-user))
      existing-posts)))
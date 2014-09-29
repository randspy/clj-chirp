(ns clj-chirp.wall
  (:require [clj-chirp.find :as find]))


(defn- attach-username-to-post [post]
  (let [user-name (:user-name post)]
    (map #(assoc % :user-name user-name) (:posts-values post))))

(defn show-wall [all-users-posts user-name]
  (if (seq all-users-posts)
    (let [user (find/find-by-user-name all-users-posts user-name)
          followed-users-names (:follows user)
          followed-users (map #(find/find-by-user-name all-users-posts %) followed-users-names)
          all-users (conj followed-users user)]
      (flatten (concat (map attach-username-to-post all-users))))
    []))

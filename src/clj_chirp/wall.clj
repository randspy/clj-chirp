(ns clj-chirp.wall
  (:require [clj-chirp.find :as find]))


(defn- flatten-user [posts]
  (let [user-name (:user-name posts)]
    (map #(assoc % :user-name user-name) (:posts posts))))

(defn show-wall [posts user-name]
  (if (seq posts)
    (let [user (find/find-by-user-name posts user-name)
          followed-users-names (:follows user)
          followed-users (map #(find/find-by-user-name posts %) followed-users-names)
          all-users (conj followed-users user)]
      (flatten (concat (map flatten-user all-users))))
    []))

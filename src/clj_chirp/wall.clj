(ns clj-chirp.wall
  (:require [clj-chirp.find :as find]))


(defn show-wall [posts user-name]
  (if (seq posts)
    (let [user (find/find-by-user posts user-name)
          followed-users-names (:follows user)
          followed-users (map #(find/find-by-user posts %) followed-users-names)]
      (conj followed-users user))
    []))

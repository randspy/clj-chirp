(ns clj-chirp.reading
  (:require [clj-chirp.find :as find]))

(defn read-user-posts [posts user-name]
  (if (seq posts)
    (let [user-posts (find/find-by-user-name posts user-name)]
      (if (seq user-posts)
        user-posts
        {}))
    {}))
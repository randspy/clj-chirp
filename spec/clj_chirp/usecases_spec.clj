(ns clj-chirp.usecases-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.usecases :refer :all]
            [clj-chirp.content :as content]
            [clj-chirp.posting :as posting]
            [clj-time.core :as time]))

(describe "Usecase execution."
          (with timestamp (time/date-time 2014))
          (with! post (atom [{:user-name "user robert"
                              :posts [{:content "post" :timestamp (time/date-time 2014)}
                                      {:content "second post" :timestamp (time/date-time 2014)}]}]))
          (describe "Posing usecase is executed."
                    (it "Posts are updated."
                        (should= [{:user-name  "user robert"
                                   :posts [{:content "post" :timestamp @timestamp}
                                           {:content "second post" :timestamp (time/date-time 2014)}]}
                                  {:user-name  "user bob"
                                   :posts [{:content "post" :timestamp @timestamp}]}]
                                 (usecase-execution @post "user bob -> post" @timestamp))))
          (describe "Reading usecase is executed."
                    (around [it]
                            (with-out-str (it)))
                    (it "Post is send to the standard output"
                        (should= (str "post (1 month ago)\n"
                                      "second post (1 month ago)\n")
                                 (with-out-str
                                   (usecase-execution @post "user robert" (time/date-time 2014 02))))))
          (describe "Following usecase is executed."
                    (with! post (atom [{:user-name "user bob"}
                                       {:user-name "user robert"}]))
                    (it "Posts are updated."
                        (should= [{:user-name  "user bob"
                                   :follows ["user robert"]}
                                  {:user-name  "user robert"}]
                                 (usecase-execution @post "user bob follows user robert" @timestamp)))))
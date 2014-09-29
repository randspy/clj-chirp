(ns clj-chirp.usecases-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.usecases :refer :all]
            [clj-chirp.content :as content]
            [clj-chirp.posting :as posting]
            [clj-time.core :as time]))

(describe "clj-chirp.usecases"
          (with timestamp (time/date-time 2014))
          (with user "user")
          (with! post (atom [{:user-name "user"
                              :posts-values [{:content "content one" :timestamp (time/date-time 2014)}
                                             {:content "content two" :timestamp (time/date-time 2014)}]}]))
          (describe "Posing usecase is executed."
                    (with new-user "new user")
                    (with content "content")
                    (with usecase-token "->")
                    (it "Posts are updated."
                        (should= (conj @@post
                                       {:user-name @new-user
                                        :posts-values [{:content @content :timestamp @timestamp}]})
                                 (usecase-execution @post (str @new-user @usecase-token @content) @timestamp))))
          (describe "Following usecase is executed."
                    (with followed-user "followed user")
                    (with! post (atom [{:user-name "user"}
                                       {:user-name "followed user"}]))
                    (with usecase-token "follows")
                    (it "Posts are updated."
                        (should= [{:user-name  @user
                                   :follows    #{@followed-user}}
                                  {:user-name  @followed-user}]
                                 (usecase-execution @post (str @user @usecase-token @followed-user) timestamp)))
                    (describe "Follows user with post marker in content."
                              (with followed-user "->")
                              (with! post (atom [{:user-name "user"}
                                                 {:user-name "->"}]))
                              (it "New followed is added intead of a new post."
                                  (should= [{:user-name  @user
                                             :follows #{@followed-user}}
                                            {:user-name  @followed-user}]
                                           (usecase-execution @post (str @user @usecase-token @followed-user) timestamp)))))
          (describe "Wall usecase is executed."
                    (with! post (atom [{:user-name "user"
                                        :posts-values [{:content "content" :timestamp (time/date-time 2014)}]
                                        :follows ["followed user"]}
                                       {:user-name "followed user"
                                        :posts-values [{:content "content" :timestamp (time/date-time 2014 02)}]}]))
                    (with usecase-token "wall")
                    (with timestamp-two-months-into-future (time/date-time 2014 03))
                    (with followed-user "followed user")
                    (it "Post is send to the standard output"
                        (should= (str @followed-user " - content (1 month ago)\n"
                                      @user " - content (2 months ago)\n")
                                 (with-out-str
                                   (usecase-execution @post
                                                      (str @user @usecase-token)
                                                      @timestamp-two-months-into-future)))))
          (describe "Reading usecase is executed."
                    (with timestamp-one-months-into-future (time/date-time 2014 02))
                    (it "Post is send to the standard output"
                        (should= (str "content two (1 month ago)\n"
                                      "content one (1 month ago)\n")
                                 (with-out-str
                                   (usecase-execution @post @user @timestamp-one-months-into-future))))))
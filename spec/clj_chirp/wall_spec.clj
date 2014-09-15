(ns clj-chirp.wall-spec
  (:require [speclj.core :refer :all]
            [clj-chirp.wall :refer :all]
            [clj-time.core :as time]))

(describe "clj-chirp.wall"
          (with user "user")
          (describe "There are no user posts."
                    (it "Returns nothing."
                        (should= []
                                 (show-wall [] @user))))
          (describe "User is not following anybody."
                    (with not-followed-user "not followed user")
                    (it "Returns user posts."
                        (should= [{:user-name @user}]
                                 (show-wall [{:user-name @user}
                                             {:user-name @not-followed-user}]
                                            @user))))
          (describe "User is following other users."
                    (with followed-user "followed user")
                    (it "Returns user posts and followed users."
                        (should= [{:user-name @user :follows [@followed-user]}
                                  {:user-name @followed-user}]
                                 (show-wall [{:user-name @user :follows [@followed-user]}
                                             {:user-name @followed-user}]
                                            @user)))))
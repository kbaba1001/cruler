(ns cruler.log
  (:require [io.aviso.ansi :as ansi]
            [cruler.config :as config]))

(def ^:dynamic *level* :info)

(def ^:dynamic *colorize?* true)

(defn- colorize
  [color xs]
  (if (and @config/colorize *colorize?*)
    (map color xs)
    xs))

(defn info
  [& more]
  (when-not (#{:warn :error} *level*)
    (apply println (colorize ansi/blue more))))

(defn warn
  [& more]
  (when-not (= :error *level*)
    (binding [*out* *err*]
      (apply println (colorize ansi/yellow more)))))

(defn error
  [& more]
  (binding [*out* *err*]
    (apply println (colorize ansi/red more))))

(ns rum.mdl
  (:refer-clojure :exclude [list])
  #?(:cljs (:require-macros [rum.mdl :refer [defmdl]]))
  (:require
   [rum.core :as rum #?@(:clj  [:refer [defc defcc defcs]]
                         :cljs [:refer-macros [defc defcc defcs]])]
   [classname.core :refer [classname]]
   #?(:cljs
      [cljsjs.material])))

(defn- v [val xs] (reduce conj val xs))

(defn- rename-kw [ks kmap]
  (for [k ks]
    (if-let [new (kmap k)]
      new
      (str "mdl-" (name k)))))

(def mdl-component
  {:button      "MaterialButton"
   :table       "MaterialDataTable"
   :layout      "MaterialLayout"
   :layout-tab  "MaterialLayoutTab"
   :menu        "MaterialMenu"
   :progress    "MaterialProgress"
   :ripple      "MaterialRipple"
   :snackbar    "MaterialSnackbar"
   :spinner     "MaterialSpinner"
   :tabs        "MaterialTabs"
   :textfield   "MaterialTextfield"
   :tooltip     "MaterialTooltip"
   :checkbox    "MaterialCheckbox"
   :raido       "MaterialRadio"
   :icon-toggle "MaterialIconToggle"
   :switch      "MaterialSwitch"})

(def mdl-required
  {:button    "mdl-button mdl-js-button"
   :checkbox  "mdl-checkbox mdl-js-checkbox"
   :radio     "mdl-radio mdl-js-radio"
   :slider    "mdl-slider mdl-js-slider"
   :table     "mdl-data-table mdl-js-data-table"
   :textfield "mdl-textfield mdl-js-textfield"})

(def mdl-optional
  {:badge {:no-background :mdl-badge--no-background
           :overlap       :mdl-badge--overlap
           ;; custom
           :icon          :material-icons}

   :button {:raised   :mdl-button--raised
            :fab      :mdl-button--fab
            :mini-fab :mdl-button--mini-fab
            :icon     :mdl-button--icon
            :colored  :mdl-button--colored
            :primary  :mdl-button--primary
            :accent   :mdl-button--accent
            :ripple   :mdl-js-ripple-effect}

   :card   {:border :mdl-card--border}

   :list {:two              :mdl-list__item--two-line
          :three            :mdl-list__item--three-line
          :primary          :mdl-list__item-primary-content
          :secondary        :mdl-list__item-secondary-content
          :secondary-info   :mdl-list__item-secondary-info
          :secondary-action :mdl-list__item-secondary-action
          :avatar           :mdl-list__item-avatar
          :icon             :mdl-list__item-icon
          :sub              :mdl-list__item-sub-title
          :body             :mdl-list__item-text-body}

   :menu {:top-left     :mdl-menu--top-left
          :top-right    :mdl-menu--top-right
          :bottom-left  :mdl-menu--bottom-left
          :bottom-right :mdl-menu--bottom-right
          :divider      :mdl-menu__item--full-bleed-divider
          :ripple :mdl-js-ripple-effect}

   :toggle {:ripple :mdl-js-ripple-effect}

   :layout {:fixed-tabs               :mdl-layout--fixed-tabs
            :fixed-drawer             :mdl-layout--fixed-drawer
            :fixed-header             :mdl-layout--fixed-header
            :no-drawer-button         :mdl-layout--no-drawer-button
            :no-desktop-drawer-button :mdl-layout--no-desktop-drawer-button}

   :grid {:no-spacing :mdl-grid--no-spacing}

   :cell {:stretch :mdl-cell--stretch, :top :mdl-cell--top, :middle :mdl-cell--middle, :bottom :mdl-cell--bottom, :hide-desktop :mdl-cell--hide-desktop, :hide-tablet :mdl-cell--hide-tablet, :hide-phone :mdl-cell--hide-phone, :mdl-cell--order-12-phone :order-12-phone, :mdl-cell--order-12-tablet :order-12-tablet, :mdl-cell--order-12-desktop :order-12-desktop, :mdl-cell--order-12 :order-12, :mdl-cell--order-11-phone :order-11-phone, :mdl-cell--order-11-tablet :order-11-tablet, :mdl-cell--order-11-desktop :order-11-desktop, :mdl-cell--order-11 :order-11, :mdl-cell--order-10-phone :order-10-phone, :mdl-cell--order-10-tablet :order-10-tablet, :mdl-cell--order-10-desktop :order-10-desktop, :mdl-cell--order-10 :order-10, :mdl-cell--order-9-phone :order-9-phone, :mdl-cell--order-9-tablet :order-9-tablet, :mdl-cell--order-9-desktop :order-9-desktop, :mdl-cell--order-9 :order-9, :mdl-cell--order-8-phone :order-8-phone, :mdl-cell--order-8-tablet :order-8-tablet, :mdl-cell--order-8-desktop :order-8-desktop, :mdl-cell--order-8 :order-8, :mdl-cell--order-7-phone :order-7-phone, :mdl-cell--order-7-tablet :order-7-tablet, :mdl-cell--order-7-desktop :order-7-desktop, :mdl-cell--order-7 :order-7, :mdl-cell--order-6-phone :order-6-phone, :mdl-cell--order-6-tablet :order-6-tablet, :mdl-cell--order-6-desktop :order-6-desktop, :mdl-cell--order-6 :order-6, :mdl-cell--order-5-phone :order-5-phone, :mdl-cell--order-5-tablet :order-5-tablet, :mdl-cell--order-5-desktop :order-5-desktop, :mdl-cell--order-5 :order-5, :mdl-cell--order-4-phone :order-4-phone, :mdl-cell--order-4-tablet :order-4-tablet, :mdl-cell--order-4-desktop :order-4-desktop, :mdl-cell--order-4 :order-4, :mdl-cell--order-3-phone :order-3-phone, :mdl-cell--order-3-tablet :order-3-tablet, :mdl-cell--order-3-desktop :order-3-desktop, :mdl-cell--order-3 :order-3, :mdl-cell--order-2-phone :order-2-phone, :mdl-cell--order-2-tablet :order-2-tablet, :mdl-cell--order-2-desktop :order-2-desktop, :mdl-cell--order-2 :order-2, :mdl-cell--order-1-phone :order-1-phone, :mdl-cell--order-1-tablet :order-1-tablet, :mdl-cell--order-1-desktop :order-1-desktop, :mdl-cell--order-1 :order-1, :11-offset :mdl-cell--11-offset, :10-offset :mdl-cell--10-offset, :9-offset :mdl-cell--9-offset, :8-offset :mdl-cell--8-offset, :7-offset :mdl-cell--7-offset, :6-offset :mdl-cell--6-offset, :5-offset :mdl-cell--5-offset, :4-offset :mdl-cell--4-offset, :3-offset :mdl-cell--3-offset, :2-offset :mdl-cell--2-offset, :1-offset :mdl-cell--1-offset, :1 :mdl-cell--1-col, :2 :mdl-cell--2-col, :3 :mdl-cell--3-col, :4 :mdl-cell--4-col, :5 :mdl-cell--5-col, :6 :mdl-cell--6-col, :7 :mdl-cell--7-col, :8 :mdl-cell--8-col, :9 :mdl-cell--9-col, :10 :mdl-cell--10-col, :11 :mdl-cell--11-col, :12 :mdl-cell--12-col, :11-offset-desktop :mdl-cell--11-offset-desktop, :10-offset-desktop :mdl-cell--10-offset-desktop, :9-offset-desktop :mdl-cell--9-offset-desktop, :8-offset-desktop :mdl-cell--8-offset-desktop, :7-offset-desktop :mdl-cell--7-offset-desktop, :6-offset-desktop :mdl-cell--6-offset-desktop, :5-offset-desktop :mdl-cell--5-offset-desktop, :4-offset-desktop :mdl-cell--4-offset-desktop, :3-offset-desktop :mdl-cell--3-offset-desktop, :2-offset-desktop :mdl-cell--2-offset-desktop, :1-offset-desktop :mdl-cell--1-offset-desktop, :1-desktop :mdl-cell--1-col-desktop, :2-desktop :mdl-cell--2-col-desktop, :3-desktop :mdl-cell--3-col-desktop, :4-desktop :mdl-cell--4-col-desktop, :5-desktop :mdl-cell--5-col-desktop, :6-desktop :mdl-cell--6-col-desktop, :7-desktop :mdl-cell--7-col-desktop, :8-desktop :mdl-cell--8-col-desktop, :9-desktop :mdl-cell--9-col-desktop, :10-desktop :mdl-cell--10-col-desktop, :11-desktop :mdl-cell--11-col-desktop, :12-desktop :mdl-cell--12-col-desktop, :7-offset-tablet :mdl-cell--7-offset-tablet, :6-offset-tablet :mdl-cell--6-offset-tablet, :5-offset-tablet :mdl-cell--5-offset-tablet, :4-offset-tablet :mdl-cell--4-offset-tablet, :3-offset-tablet :mdl-cell--3-offset-tablet, :2-offset-tablet :mdl-cell--2-offset-tablet, :1-offset-tablet :mdl-cell--1-offset-tablet, :1-tablet :mdl-cell--1-col-tablet, :2-tablet :mdl-cell--2-col-tablet, :3-tablet :mdl-cell--3-col-tablet, :4-tablet :mdl-cell--4-col-tablet, :5-tablet :mdl-cell--5-col-tablet, :6-tablet :mdl-cell--6-col-tablet, :7-tablet :mdl-cell--7-col-tablet, :8-tablet :mdl-cell--8-col-tablet, :3-offset-phone :mdl-cell--3-offset-phone, :2-offset-phone :mdl-cell--2-offset-phone, :1-offset-phone :mdl-cell--1-offset-phone, :1-phone :mdl-cell--1-col-phone, :2-phone :mdl-cell--2-col-phone, :3-phone :mdl-cell--3-col-phone, :4-phone :mdl-cell--4-col-phone}

   :progress {:indeterminate :mdl-progress__indeterminate}

   :spinner {:single-color :mdl-spinner--single-color}

   :header {:scroll    :mdl-layout__header--scroll
            :waterfall :mdl-layout__header--waterfall}

   :nav {:large-screen-only :mdl-layout--large-screen-only
         :small-screen-only :mdl-layout--small-screen-only}

   :table {:selectable  :mdl-data-table--selectable
           :ascending   :mdl-data-table__header--sorted-ascending
           :descending  :mdl-data-table__header--sorted-descending
           :non-numeric :mdl-data-table__cell--non-numeric}

   :textfield {:floating-label :mdl-textfield--floating-label
               :expandable     :mdl-textfield--expandable
               :align-right    :mdl-textfield--align-right}

   :tooltip {:large  :mdl-tooltip--large
             :top    :mdl-tooltip--top
             :left   :mdl-tooltip--left
             :right  :mdl-tooltip--right
             :bottom :mdl-tooltip--bottom}

   nil {:ripple :mdl-js-ripple-effect}})

(defn- attrs-contents [xs]
  (let [[attrs]  xs
        map?     (map? attrs)
        attrs    (if map? attrs {})
        contents (if map? (rest xs) xs)]
    [attrs contents]))

(defn- contents-with-key [contents & [key]]
  (if (< (count contents) 2)
    (first contents)
    (for [e contents :let [key (gensym key)]]
      (cond
        (vector? e)
        (if (map? (get e 1))
          (assoc-in e [1 :key] key)
          (apply vector (first e) {:key key} (rest e)))
        (string? e)
        [:span {:key key} e]
        :else (rum/with-key e key)))))

(defn mdl-attrs
  ([attrs]
   (mdl-attrs attrs nil))
  ([{:keys [mdl] :as attrs} key]
   (if (empty? mdl)
     attrs
     (-> attrs
       (update :class classname (rename-kw mdl (mdl-optional key)))
       (dissoc :mdl)))))

(defn rum-mdl-attrs
  [{:keys [rum-mdl] :as attrs}]
  (if rum-mdl
    (-> attrs
      (update :class classname (mdl-required rum-mdl))
      (dissoc :rum-mdl))
    attrs))

(defn rum-mdl [key]
  {:transfer-state
   (fn [_ {args :rum/args :as new}]
     (let [[attrs contents] (attrs-contents args)
           attrs    (mdl-attrs attrs key)
           contents (contents-with-key contents key)]
       (assoc new :rum/args [attrs contents])))
   :will-mount
   (fn [{args :rum/args :as state}]
     ;; type of rum/args is cljs.core/IndexedSeq
     ;; args: [attr? content*]
     (let [[attrs contents] (attrs-contents args)
           attrs    (mdl-attrs attrs key)
           contents (contents-with-key contents key)]
       #_(println (map type contents))
       (assoc state :rum/args [attrs contents])))})

#?(:clj
   (defmacro defmdl
     {:arglists '([name mdl-key? docstring? binding & body])}
     [& xs]
     (let [[name]    xs
           xs        (rest xs)
           ys        (take-while (complement vector?) xs)
           mdl-key   (first (filter keyword? ys))
           docstring (first (filter string? ys))
           docstring (if docstring docstring "")
           xs        (drop-while (complement vector?) xs)
           binding   (first xs)
           a-binding (first binding)
           body      (rest xs)
           arglists  '([& contents] [attrs & contents])]
       `(defn ~name ~docstring
          {:arglists '~arglists}
          [& xs#]
          (let [~binding   (attrs-contents xs#)
                ~a-binding (mdl-attrs ~a-binding ~mdl-key)]
            ~@body)))))

#?(:cljs
   (defn upgrade-element [el]
     (js/componentHandler.upgradeElement el)))

#?(:cljs
   (defn downgrade-elements [& elements]
     (js/componentHandler.downgradeElements (to-array elements))))

#?(:cljs
   (defn listen-component-upgraded
     {:style/indent 1}
     [el callback]
     (.addEventListener el "mdl-componentupgraded" callback)))

#?(:cljs
   (defn listen-component-downgraded
     {:style/indent 1}
     [el callback]
     (.addEventListener el "mdl-componentdowngraded" callback)))

(def component-handler
  "only for `mdl-js-*' classed component"
  #?(:cljs
     {:did-mount
      (fn [state]
        (let [rc  (:rum/react-component state)
              dom (js/ReactDOM.findDOMNode rc)]
          (upgrade-element dom)
          (assoc state :mdl/dom dom)))
      :will-unmount
      (fn [state]
        (let [rc  (:rum/react-component state)
              dom (js/ReactDOM.findDOMNode rc)]
          (downgrade-elements dom)
          (dissoc state :mdl/dom)))}))

(defn icon
  ([font] (icon nil font))
  ([attrs font] [:i.material-icons ^:attrs attrs font])
  ([tag attrs font] [tag ^:attrs (update attrs :class classname :material-icons) font]))

;;; badges

(defn badge-attrs [attrs]
  (-> attrs
    (rum-mdl-attrs)
    (mdl-attrs :badge)))

(defc badge < (rum-mdl :badge) rum/static
  [& [attrs content]]
  [:span.mdl-badge ^:attrs attrs content])

;;; buttons

(defn button-attrs [attrs]
  (-> attrs
    (rum-mdl-attrs)
    (mdl-attrs :button)))

(defc button < (rum-mdl :button) component-handler rum/static
  [& [attrs content]]
  [:button.mdl-button.mdl-js-button ^:attrs attrs content])

(defc label-button < (rum-mdl :button) component-handler rum/static
  [& [attrs content]]
  [:label.mdl-button.mdl-js-button ^:attrs attrs content])

;;; cards

(defc card < (rum-mdl :card) rum/static
  [& [attrs content]]
  [:.mdl-card ^:attrs attrs content])

(defn card-title
  [title]
  [:.mdl-card__title
   (if (string? title)
     [:h2.mdl-card__title-text title]
     title)])

(defn card-text [text]
  [:.mdl-card__supporting-text text])

(defmdl card-media :card [attrs [content]]
  [:.mdl-card__media ^:attrs attrs content])

(defmdl card-action :card [attrs [content]]
  [:.mdl-card__actions ^:attrs attrs content])

(defmdl card-menu :card [attrs [content]]
  [:.mdl-card__menu ^:attrs attrs content])

;;; dialogs

;;; layout

(defc layout < (rum-mdl :layout) component-handler rum/static
  [& [attrs contents]]
  [:.mdl-layout.mdl-js-layout ^:attrs attrs contents])

(defn layout-spacer [] [:.mdl-layout-spacer])

(defn layout-title [title] [:.mdl-layout-title title])

(defc header < (rum-mdl :header) rum/static
  [& [attrs contents]]
  [:header.mdl-layout__header ^:attrs attrs
   [:.mdl-layout__header-row contents]])

(defc nav < (rum-mdl :nav) rum/static
  [& [attrs contents]]
  [:nav.mdl-navigation ^:attrs attrs contents])

(defmdl link "<a>" [attrs contents]
  [:a.mdl-navigation__link ^:attrs attrs (contents-with-key contents :link)])

(defmdl drawer :drawer [attrs contents]
  (v [:.mdl-layout__drawer ^:attrs attrs] contents))

(defmdl main-content :layout [attrs contents]
  (v [:main.mdl-layout__content ^:attrs attrs] contents))

(defc grid < (rum-mdl :grid) rum/static
  [& [attrs contents]]
  [:.mdl-grid ^:attrs attrs contents])

(defc cell < (rum-mdl :cell) rum/static
  [& [attrs contents]]
  [:.mdl-cell ^:attrs attrs contents])

;;; lists

(defc list < (rum-mdl :list) component-handler rum/static
  [& [attrs contents]]
  [:ul.mdl-list ^:attrs attrs contents])

(defn li
  {:arglists '([attrs? secondary?])}
  [& xs]
  (let [[attrs [secondary]] (attrs-contents xs)
        attrs            (mdl-attrs attrs :list)
        {:keys [icon avatar content sub body]} attrs
        icon     (when icon [:i.material-icons.mdl-list__item-icon icon])
        avatar   (when avatar [:i.material-icons.mdl-list__item-avatar avatar])
        sub      (when sub [:span.mdl-list__item-sub-title sub])
        body     (when body [:span.mdl-list__item-text-body body])
        attrs    (dissoc attrs :icon :avatar :content :sub :body)]
    [:li.mdl-list__item ^:attrs attrs
     [:span.mdl-list__item-primary-content {}
      icon avatar content sub body]
     (when secondary
       (let [{:keys [info action]} secondary
             info   (when info [:span.mdl-list__item-secondary-info info])
             action (when action [:a.mdl-list__item-secondary-action action])]
         [:span.mdl-list__item-secondary-content {}
          info action]))]))

;;; loading

(defc progress < (rum-mdl :progress) component-handler
  #?(:cljs
     {:transfer-state
      (fn [old new]
        (let [{[{:keys [progress buffer]}] :rum/args} new
              dom (:mdl/dom old)
              component (aget dom "MaterialProgress")]
          (when progress
            (.. component (setProgress progress)))
          (when buffer
            (.. component (setBuffer buffer)))
          (assoc new :mdl/dom dom)))
      :did-mount
      (fn [state]
        (let [{[{:keys [progress buffer]}] :rum/args
               dom :mdl/dom} state
              component (aget dom "MaterialProgress")]
          (when progress
            (.. component (setProgress progress)))
          (when buffer
            (.. component (setBuffer buffer))))
        state)}) rum/static
  [& [attrs]]
  [:.mdl-progress.mdl-js-progress ^:attrs attrs])

(defc spinner < (rum-mdl :spinner) component-handler rum/static
  [& [attrs]]
  [:.mdl-spinner.mdl-js-spinner ^:attrs
   (let [{:keys [is-active]} attrs]
     (update attrs :class classname {:is-active is-active}))])

;;; menus

(defc ^{:style/indent 1} menu < (rum-mdl :menu) component-handler rum/static
  [& [attrs contents]]
  [:ul.mdl-menu.mdl-js-menu ^:attrs attrs contents])

(defmdl menu-item :menu [attrs [content]]
  [:li.mdl-menu__item ^:attrs attrs content])

;;; sliders

(defc slider < (rum-mdl :slider) component-handler rum/static
  [& [attrs]]
  [:input.mdl-slider.mdl-js-slider ^:attrs
   (-> {:type "range"
        :on-change (fn [_])
        :min "0" :max "100"}
     (merge attrs))])

;;; snackbar

(defc snackbar < component-handler rum/static
  {:did-mount
   (fn [state]
     #?(:cljs
        (let [this (:rum/react-component state)
              dom  (:mdl/dom state)
              m    (aget dom "MaterialSnackbar")]
          (aset this "show-snackbar"
                (fn [o] (. m (showSnackbar o))))))
     state)}
  [& [{:keys [action] :as attrs} contents]]
  [:.mdl-snackbar.mdl-js-snackbar ^:attrs attrs
   [:.mdl-snackbar__text]
   [:button.mdl-snackbar__action (merge {:type "button"} action)]])

;;; toggles

(defn toggle
  "workaround mixin"
  [component]
  #?(:cljs
     {:did-mount
      (fn [state]
        (let [{[{:keys [checked disabled mdl]}] :rum/args
               this :rum/react-component
               dom  :mdl/dom} state
              m (aget dom component)]
          (when disabled                ; (. m (enable))
            (. m (disable)))
          (case component
            "MaterialSwitch"
            (when checked               ; (. m (off))
              (. m (on)))
            (when checked               ; (. m (uncheck))
              (. m (check))))
          (when (some #{:ripple} mdl)
            (let [selector (str "." (aget m "CssClasses_" "RIPPLE_CONTAINER"))
                  ripple   (.querySelector dom selector)] ; could be ".mdl-js-ripple-effect"
              (upgrade-element ripple)
              (listen-component-downgraded dom
                                           #(downgrade-elements ripple)))))
        state)}))

(defc checkbox < component-handler (toggle "MaterialCheckbox")
  [{:keys [input label for] :as attrs}]
  [:label.mdl-checkbox.mdl-js-checkbox ^:attrs
   (-> attrs (mdl-attrs :toggle) (dissoc :input :label))
   [:input.mdl-checkbox__input ^:attrs
    (-> {:type "checkbox" :id for}
      (merge input))]
   [:span.mdl-checkbox__label label]])

(defc radio < component-handler (toggle "MaterialRadio") rum/static
  [{:keys [input label for] :as attrs}]
  [:label.mdl-radio.mdl-js-radio ^:attrs
   (-> attrs (mdl-attrs :toggle) (dissoc :input :label))
   [:input.mdl-radio__button ^:attrs
    (-> {:type "radio" :id for}
      (merge input))]
   [:span.mdl-radio__label label]])

(defc icon-toggle < component-handler (toggle "MaterialIconToggle") rum/static
  [{:keys [input label for] :as attrs}]
  [:label.mdl-icon-toggle.mdl-js-icon-toggle ^:attrs
   (-> attrs (mdl-attrs :toggle) (dissoc :input :label))
   [:input.mdl-icon-toggle__input ^:attrs
    (-> {:type "checkbox" :id for}
      (merge input))]
   [:i.material-icons.mdl-icon-toggle__label label]])

(defc switch < component-handler (toggle "MaterialSwitch") rum/static
  [{:keys [input for] :as attrs}]
  [:label.mdl-switch.mdl-js-switch ^:attrs
   (-> attrs (mdl-attrs :toggle) (dissoc :input))
   [:input.mdl-switch__input ^:attrs
    (-> {:type "checkbox" :id for}
      (merge input))]
   [:span.mdl-switch__label]])

;;; tables

(defc table < (rum-mdl :table) component-handler rum/static
  [& [attrs contents]]
  [:table.mdl-data-table.mdl-js-data-table ^:attrs attrs contents])

(defn thead
  ([heads]
   (thead nil heads))
  ([vattrs heads]
   [:thead
    (let [idx-attrs (into {} (comp
                              (map-indexed #(vector %1 %2))
                              (remove #(nil? (second %))))
                          vattrs)]
      (v [:tr] (map #(if-let [attrs (idx-attrs %1)]
                       [:th ^:attrs (mdl-attrs attrs :table) %2]
                       [:th %2])
                    (range) heads)))]))

(defn tbody
  ([data]
   [tbody nil data])
  ([vattrs data]
   [:tbody
    (contents-with-key
     (let [idx-attrs (into {} (comp
                               (map-indexed #(vector %1 %2))
                               (remove #(nil? (second %))))
                           vattrs)]
       (for [row data]
         (v [:tr] (map #(if-let [attrs (idx-attrs %1)]
                          [:td ^:attrs (mdl-attrs attrs :table) %2]
                          [:td %2])
                       (range) row)))))]))

;;; textfields

(defc textfield < (rum-mdl :textfield) component-handler rum/static
  [& [attrs contents]]
  [:.mdl-textfield.mdl-js-textfield ^:attrs attrs contents])

(defmdl textfield-input :textfield [attrs [content]]
  [:input.mdl-textfield__input ^:attrs (merge {:type "text"} attrs) content])

(defmdl textfield-label :textfield [attrs [content]]
  [:label.mdl-textfield__label ^:attrs attrs content])

(defmdl textfield-error :textfield [attrs [content]]
  [:span.mdl-textfield__error ^:attrs attrs content])

(defmdl textfield-textarea :textfield [attrs [content]]
  [:textarea.mdl-textfield__input ^:attrs (merge {:type "text"} attrs) content])

(defmdl textfield-expandable-holder :textfield [attrs contents]
  (v [:.mdl-textfield__expandable-holder ^:attrs attrs] contents))

;;; tooltips

(defc tooltip < (rum-mdl :tooltip) component-handler rum/static
  [& [attrs content]]
  [:.mdl-tooltip ^:attrs attrs content])

EinkaufslisteRor::Application.routes.draw do
  resources :addresses

  resources :stores

  resources :articles

  resources :shoppinglists

  resources :users
  
  resources :listings

  # custom methods of User
  get "users/select/:id" => "users#select", as: "select_user"
  
  
  # custom methods of Shoppinglist
  get "shoppinglists/select/:id" => "shoppinglists#select", as: "select_shoppinglist" 
  
  
  # custom methods of Listings
  post "listings/add" => "listings#add", as: "add"
  
  post "listings/:list_id/edit/:article_id" => "listings#edit", as: "edit"
  
  
  # custom methods of Stores
  get "stores/new_article_store_ref/:article_id" => "stores#new_article_store_ref", as: "new_article_store_ref"
  
  post "stores/create_article_store_ref/:article_id" => "stores#create_article_store_ref", as: "create_article_store_ref"
  
  post "stores/add_article_store_ref/:article_id" => "stores#add_article_store_ref", as: "add_article_store_ref"
  
  post "stores/:id/delete_address_ref/:address_id" => "stores#delete_address_ref", as: "delete_address_ref"
  
  post "stores/:id/delete_article_ref/:article_id" => "stores#delete_article_ref", as: "delete_article_ref"
  
  post "stores/add_address/:id" => "stores#add_address", as: "add_address"
  
  post "stores/add_article/:id" => "stores#add_article", as: "add_article"
 
 
 # custom methods of Address
  get "addresses/new_store_address_ref/:store_id" => "addresses#new_store_address_ref", as: "new_store_address_ref"
 
  post "addresses/create_store_address_ref/:store_id" => "addresses#create_store_address_ref", as: "create_store_address_ref"
  
  post "addresses/add_store_address_ref/:store_id" => "addresses#add_store_address_ref", as: "add_store_address_ref"
 
 
 # custom methods of Articles
  get "articles/new_store_article_ref/:store_id" => "articles#new_store_article_ref", as: "new_store_article_ref"
 
  post "articles/create_store_store_article_ref/:store_id" => "articles#create_store_article_ref", as: "create_store_article_ref"
 
  post "articles/add_store_article_ref/:store_id" => "articles#add_store_article_ref", as: "add_store_article_ref"
 
  post "articles/:id/delete_store_ref/:store_id" => "articles#delete_store_ref", as: "delete_store_ref"
  
  post "articles/add_store/:id" => "articles#add_store", as: "add_store"
  
  # The priority is based upon order of creation:
  # first created -> highest priority.

  # Sample of regular route:
  #   match 'products/:id' => 'catalog#view'
  # Keep in mind you can assign values other than :controller and :action

  # Sample of named route:
  #   match 'products/:id/purchase' => 'catalog#purchase', :as => :purchase
  # This route can be invoked with purchase_url(:id => product.id)

  # Sample resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Sample resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Sample resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Sample resource route with more complex sub-resources
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', :on => :collection
  #     end
  #   end

  # Sample resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end

  # You can have the root of your site routed with "root"
  # just remember to delete public/index.html.
  root :to => 'welcome#index'

  # See how all your routes lay out with "rake routes"

  # This is a legacy wild controller route that's not recommended for RESTful applications.
  # Note: This route will make all actions in every controller accessible via GET requests.
  # match ':controller(/:action(/:id))(.:format)'
end

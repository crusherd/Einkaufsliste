EinkaufslisteRor::Application.routes.draw do
  resources :addresses

  resources :stores

  resources :articles

  resources :shoppinglists

  resources :users
  
  resources :listings

  get "users/select/:id" => "users#select", as: "select_user"
  
  get "shoppinglists/select/:id" => "shoppinglists#select", as: "select_shoppinglist" 
  
  post "listings/add" => "listings#add", as: "add"
  
  post "stores/add_address/:id" => "stores#add_address", as: "add_address"
  
  post "stores/:id/delete_address/:address_id" => "stores#delete_address", as: "delete_address_store"
 
  get "addresses/new_addres_ref/:store_id" => "addresses#new_address_ref", as: "new_address_ref"
 
  post "addresses/create_address_ref/:store_id" => "addresses#create_address_ref", as: "create_address_ref"
  
  post "addresses/add_address_ref/:store_id" => "addresses#add_address_ref", as: "add_address_ref"
 
  post "articles/:id/delete_store_ref/:store_id" => "articles#delete_store_ref", as: "delete_store_ref"
  
  
  
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

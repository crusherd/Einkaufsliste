class Article < ActiveRecord::Base
  
  # relationships
  has_and_belongs_to_many :shoppingList # m-to-m relation
  has_and_belongs_to_many :store
  #has_and_belongs_to_many :purchaseHistory
  
  # owner
  
  # accessible attributes 
  attr_accessible :name, :price, :store, :store_id, :shoppingList, :shoppingList_id
  
  # validation
  validates :name, :presence => true, :allow_nil => false, :allow_blank => false
end

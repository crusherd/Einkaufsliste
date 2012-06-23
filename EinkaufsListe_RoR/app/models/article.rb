class Article < ActiveRecord::Base
  
  # relationships
  has_and_belongs_to_many :shoppingList # m-to-m relation
  has_and_belongs_to_many :location
  has_and_belongs_to_many :purchaseHistory
  
  # owner
  
  # accessible attributes 
  attr_accessible :name, :price, :location
  
  # validation
  validates :location, :presence => true, :allow_nil => false, :allow_blank => false
  validates :shoppingList, :presence => true, :allow_nil => false, :allow_blank => false
end

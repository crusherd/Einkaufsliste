class Article < ActiveRecord::Base
  
  # relationships
  has_and_belongs_to_many :shopping_list # m-to-m relation
  has_and_belongs_to_many :location
  has_and_belongs_to_many :purchase_history
  
  # owner
  
  # accessible attributes 
  attr_accessible :name, :price, :location
  
  # validation
  validates :location, :presence => true
end

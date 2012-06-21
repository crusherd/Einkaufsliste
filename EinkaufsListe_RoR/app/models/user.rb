class User < ActiveRecord::Base
  
  # relationships
  has_many :shoppingList, :dependent => :destroy
  
  # owner
  
  # accessible attributes 
  attr_accessible :username, :shoppingList
  
  # validation
  validates :username, :presence => true, :uniqueness => true
  validates :shoppingList, :presence => false, :allow_nil => true, :allow_blank => true
end

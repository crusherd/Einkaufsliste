class User < ActiveRecord::Base
  
  # relationships
  has_many :shoppingList, :dependent => :destroy
  
  # owner
  
  # accessible attributes 
  attr_accessible :username
  
  # validation
  validates :username,    :presence => true
end

class ShoppingList < ActiveRecord::Base
  
  # relationships
  #has_one :user
  has_and_belongs_to_many :article # m-to-m relation
  
  # owner
  belongs_to :user
  
  # accessible attributes 
  attr_accessible :creationDate, :user, :article
  
  # validation
  validates :user, :presence => true, :allow_nil => false, :allow_blank => false
end

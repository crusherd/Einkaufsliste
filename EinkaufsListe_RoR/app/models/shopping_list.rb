class ShoppingList < ActiveRecord::Base
  
  # relationships
  has_one :owner
  has_and_belongs_to_many :article # m-to-m relation
  
  # owner
  belongs_to :owner
  
  # accessible attributes 
  attr_accessible :creationDate
  
  # validation
  validates :owner,    :presence => true
end

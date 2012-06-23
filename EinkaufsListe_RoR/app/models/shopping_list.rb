class ShoppingList < ActiveRecord::Base
  
  # relationships
  has_and_belongs_to_many :article # m-to-m relation
  
  # owner
  belongs_to :user
  
  # accessible attributes 
  attr_accessible :creationDate, :user_id, :user, :articles, :name
  
  # validation
  validates :user, :presence => true, :allow_nil => false, :allow_blank => false
  validates :name, :presence => true, :allow_nil => false, :allow_blank => false
end

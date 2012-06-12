class PurchaseHistory < ActiveRecord::Base
  
  # relationships
  has_and_belongs_to_many :article
  
  # owner
  
  # accessible attributes
  attr_accessible :purchaseDate
  
  # validation
end

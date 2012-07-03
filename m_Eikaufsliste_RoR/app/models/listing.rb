class Listing < ActiveRecord::Base
  # accessible attributes 
  attr_accessible :amount, :article_id, :shoppinglist_id
  
  # validation
  validates :amount, :presence => true, :allow_nil => false, :allow_blank => false
  
  # references
  belongs_to :shoppinglist
  belongs_to :article
end

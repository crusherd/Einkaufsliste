class Listing < ActiveRecord::Base
  # accessible attributes 
  attr_accessible :amount, :article_id, :shoppinglist_id
  
  # validation
  validates :amount, :presence => true, :allow_nil => false, :allow_blank => false
  validates :article_id, :presence => true, :allow_nil => false, :allow_blank => false
  validates :shoppinglist_id, :presence => true, :allow_nil => false, :allow_blank => false
  validates_numericality_of :amount, :greater_than => 0
  
  validates :article_id, :uniqueness => {:scope => :shoppinglist_id }

  # references
  belongs_to :shoppinglist
  belongs_to :article
end

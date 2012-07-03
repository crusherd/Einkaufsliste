class Article < ActiveRecord::Base
  # accessible attributes
  attr_accessible :name, :price 
  
  # validation
  validates :name, :presence => true, :allow_nil => false, :allow_blank => false
  validates :price, :presence => true, :allow_nil => false, :allow_blank => false
  # uniqueness of touple price and name
  validates :name, :uniqueness => {:scope => :price}
  
  # references
  has_many :listings, :dependent => :destroy
  has_many :shoppinglists, :through => :listings
end

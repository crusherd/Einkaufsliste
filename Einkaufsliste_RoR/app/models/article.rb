class Article < ActiveRecord::Base
  #
  attr_accessible :name, :price
  
  #
  
  # references
  has_many :listings
  has_many :shoppinglists, :through => :listings
end

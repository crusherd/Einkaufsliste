class Article < ActiveRecord::Base
  #
  attr_accessible :name, :price
  
  #
  
  # references
  has_and_belongs_to_many :shoppinglists
end

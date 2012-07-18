class Address < ActiveRecord::Base
  # accessible attributes
  attr_accessible :city, :street, :zipCode
  
  # references
  has_and_belongs_to_many :stores
  
end

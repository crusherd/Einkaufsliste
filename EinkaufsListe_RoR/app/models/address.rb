class Address < ActiveRecord::Base
  
  # relationships
  has_and_belongs_to_many :store
  
  # owner
  
  # accessible attributes
  attr_accessible :city, :country, :street, :zipCode
  
  # validation
end

class Address < ActiveRecord::Base
  belongs_to :store
  attr_accessible :city, :country, :street, :zipCode
end

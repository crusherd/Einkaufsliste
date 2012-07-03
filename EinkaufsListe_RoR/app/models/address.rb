class Address < ActiveRecord::Base
  
  # relationships
  
  # owner
  belongs_to :store
  
  # accessible attributes
  attr_accessible :city, :country, :street, :zipCode, :store_id, :store
  
  # validation
  validates :street, :presence => true, :allow_nil => false, :allow_blank => false
  validates :city, :presence => true, :allow_nil => false, :allow_blank => false
  validates :country, :presence => true, :allow_nil => false, :allow_blank => false
  validates :zipCode, :presence => true, :allow_nil => false, :allow_blank => false
end

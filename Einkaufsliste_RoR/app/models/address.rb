class Address < ActiveRecord::Base
  # accessible attributes
  attr_accessible :city, :street, :zipcode
  
  # validation
  validates :city, :presence => true, :allow_nil => false, :allow_blank => false
  validates :street, :presence => true, :allow_nil => false, :allow_blank => false
  validates :zipcode, :presence => true, :allow_nil => false, :allow_blank => false
  # uniqueness of triple 
  validates :city, :uniqueness => {:scope => [:street, :zipcode]}
  
  # references
  has_and_belongs_to_many :stores
  
end

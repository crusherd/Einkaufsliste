class Store < ActiveRecord::Base
  # accessible attributes
  attr_accessible :name
  
  # validation
  validates :name, :presence => true, :allow_nil => false, :allow_blank => false
  
  # references
  has_and_belongs_to_many :addresses
  
  # TODO: in controller:
  # if store is deleted and address has no reference to an other
  # store, we have to delete the address  
end

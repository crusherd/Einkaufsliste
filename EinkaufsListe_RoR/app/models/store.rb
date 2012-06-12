class Store < ActiveRecord::Base
  
  # relationships
  has_and_belongs_to_many :article
  has_and_belongs_to_many :address, :dependent => :destroy
  
  # owner
  
  # accessible attributes
  attr_accessible :name
  
  # validation
  
end

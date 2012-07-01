class Store < ActiveRecord::Base
  
  # relationships
  has_and_belongs_to_many :article
  has_many :address, :dependent => :destroy
  
  # owner
  
  # accessible attributes
  attr_accessible :name, :address_id
  
  # validation
  validates :name, :presence => true, :allow_nil => false, :allow_blank => false
  validates :address, :presence => true, :allow_nil => false, :allow_blank => false
end

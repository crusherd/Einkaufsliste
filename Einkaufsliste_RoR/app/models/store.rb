class Store < ActiveRecord::Base
  # accessible attributes
  attr_accessible :name, :addresses
  
  # validation
  validates :name, :presence => true, :allow_nil => false, :allow_blank => false
  
  #validate uniqueness
  validates :name, :uniqueness => true
  
  # references
  has_and_belongs_to_many :addresses
  has_and_belongs_to_many :articles
end

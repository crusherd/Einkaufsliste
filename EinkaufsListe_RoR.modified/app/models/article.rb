class Article < ActiveRecord::Base
  belongs_to :location
  attr_accessible :name, :price
end

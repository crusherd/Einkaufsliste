require 'test_helper'

class ListingTest < ActiveSupport::TestCase
  test "required attributes" do
    entry = Listing.new
    assert !entry.save
    assert entry.errors.get(:amount)
    assert entry.errors.get(:article_id)
    assert entry.errors.get(:shoppinglist_id)
  end
  
  test "unique listing entry" do
    entry = Listing.new(:amount => 1, :shoppinglist_id => 3, :article_id => 2)
    assert !entry.save
  end
end

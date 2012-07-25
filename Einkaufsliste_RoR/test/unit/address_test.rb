require 'test_helper'

class AddressTest < ActiveSupport::TestCase
  test "required attributes" do
    address = Address.new
    assert !address.valid?
    assert address.errors.get(:city)
    assert address.errors.get(:street)
    assert address.errors.get(:zipcode)
  end
  
  test "unique_address" do
    address = Address.new(:street => "Musterstrasse 12", :zipcode => "D-12354",
                          :city => "Musterstadt")
    assert !address.save
  end
  
  test "create and delete an address" do
    address = Address.new(:street => "Musterstrasse 13", :zipcode => "D-12354",
                          :city => "Musterstadt")
    assert address.save
    
    assert address.delete
  end
  
  test "associate store with address" do
    address = addresses(:Musterstrasse)
    store = stores(:Rewe)
    
    cnt_references_to_store = address.stores.count
    
    address.stores << store
    
    assert_not_equal(cnt_references_to_store, address.stores.count)
  end
end

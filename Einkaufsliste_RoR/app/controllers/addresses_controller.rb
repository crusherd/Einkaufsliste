class AddressesController < ApplicationController
  # GET /addresses
  # GET /addresses.json
  def index
    @addresses = Address.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @addresses }
    end
  end

  # GET /addresses/1
  # GET /addresses/1.json
  def show
    @address = Address.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @address }
    end
  end

  # GET /addresses/new
  # GET /addresses/new.json
  def new
    @address = Address.new
    
    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @address }
    end
  end

  # GET /addresses/1/edit
  def edit
    @address = Address.find(params[:id])
  end

  # POST /addresses
  # POST /addresses.json
  def create
    @address = Address.new(params[:address])

    respond_to do |format|
      if @address.save
        format.html { redirect_to @address, notice: 'Address was successfully created.' }
        format.json { render json: @address, status: :created, location: @address }
      else
        format.html { render action: "new" }
        format.json { render json: @address.errors, status: :unprocessable_entity }
      end
    end
  end
  
  
  def new_store_address_ref
    @address = Address.new
    @current_store = Store.find_by_id(:store_id)
    
    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @address }
    end
  end
  
  def create_store_address_ref
    @current_store = Store.find_by_id(params[:store_id])
    @address = Address.new(params[:address])
    
    @current_store.addresses << @address 
    
    respond_to do |format|
      if @address.save
        format.html { redirect_to stores_path, notice: 'Address was successfully created.' }
        format.json { render json: @current_store, status: :created, location: @current_store }
      else
        format.html { render action: "new_store_address_ref" }
        format.json { render json: @address.errors, status: :unprocessable_entity }
      end
    end
  end
  
  def add_store_address_ref
    @address = Address.new
    @current_store = Store.find_by_id(params[:store_id])
    @address_id = params[:address_id]
    
    
    respond_to do |format|
      if @address_id != ""
        @address = Address.find_by_id(@address_id)
        @current_store.addresses.delete(@address) # prohibit duplicate references to store
        @current_store.addresses << @address
        
        format.html { redirect_to stores_path, notice: 'Address was successfully created.' }
        format.json { render json: @current_store, status: :created, location: @current_store }
      else
        format.html { render action: "new_store_address_ref" }
        format.json { render json: @current_store, status: :no_action, location: @current_store }
      end
    end
  end

  # PUT /addresses/1
  # PUT /addresses/1.json
  def update
    @address = Address.find(params[:id])

    respond_to do |format|
      if @address.update_attributes(params[:address])
        format.html { redirect_to @address, notice: 'Address was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @address.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /addresses/1
  # DELETE /addresses/1.json
  def destroy
    @address = Address.find(params[:id])
    @address.destroy

    respond_to do |format|
      format.html { redirect_to addresses_url }
      format.json { head :no_content }
    end
  end
end

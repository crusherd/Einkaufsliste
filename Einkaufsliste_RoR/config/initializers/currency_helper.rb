module ActionView
  module Helpers
    module NumberHelper
      def number_to_currency_with_euro(number, options = {})
        defaults = {
          :unit => `&euro;`, #obvious
          :scale => 2,
          :separator => `,`,
          :delimiter => `.`, #thousands delimiter
          :format => "%n %u" #put a space between number (%n) and unit (%u) and unit is after number 
        }
         
        s = number_to_currency_without_euro(number, defaults.merge(options))
      end
      alias_method_chain :number_to_currency, :euro
    end
  end
end
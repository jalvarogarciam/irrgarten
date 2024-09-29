module Irrgarten
  class Shield
    # Atributos
    attr_reader :protection, :uses

    # Constructor
    def initialize(protection, uses)
      @protection = protection
      @uses = uses
    end

    # Método para realizar la protección
    def protect
      if @uses > 0
        @uses -= 1  # Decrementa el uso
        return @protection  # Devuelve el valor de protección
      end
      0  # Devuelve 0 si no hay usos disponibles
    end

    # Método para decidir si el escudo debe ser descartado
    def discard
      Dice.discard_element(@uses)  # Llama al método de la clase Dice
    end

    # Método para representar el escudo como un string
    def to_s
      "S[#{@protection}, #{@uses}]"  # Formato 'S[{protection}, {uses}]'
    end
  end
end

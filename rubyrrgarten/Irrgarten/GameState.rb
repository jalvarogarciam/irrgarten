class GameState
  # Atributos
  attr_reader :labyrinth, :players, :monsters, :current_player, :winner, :log

  # Constructor
  def initialize(labyrinth, players, monsters, current_player, winner, log)
    @labyrinth = labyrinth
    @players = players
    @monsters = monsters
    @current_player = current_player
    @winner = winner
    @log = log
  end
end

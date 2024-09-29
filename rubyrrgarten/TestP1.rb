require_relative 'Shield'
require_relative 'Weapon'
require_relative 'GameState'
require_relative 'Dice'
require_relative 'enums'


puts ".............Probando Weapon y Shield............."

# Creación de instancias de Weapon y Shield
weapon = Irrgarten::Weapon.new(2.0, 5)
shield = Irrgarten::Shield.new(3.0, 4)

# Usar métodos y probar to_s
puts weapon
puts "Weapon attack: #{weapon.attack}"
puts weapon

puts shield
puts "Shield protect: #{shield.protect}"
puts shield

puts "    ..prueba de discard().."

# Probar discard
probabilidad = []  # Array para almacenar probabilidades
for i in 0..Irrgarten::Dice.MAX_USES
  descarta = 0
  veces = 10000
  for j in 0..(veces-1)
    arma = Irrgarten::Weapon.new(2,i)
    if arma.discard
      descarta += 1
    end
  end
  probabilidad << Float(descarta)/Float(veces)
end

(0..5).each do |i|                  # MAX_USES = 5
  puts "    p(discard() == true) para uses=#{i} -> #{probabilidad[i] * 100}%"
end

puts "\n\n.............Probando Enumerados............."

# Probar enumerados

dir = Irrgarten::Directions::UP
character = Irrgarten::GameCharacter::PLAYER
orientation = Irrgarten::Orientation::VERTICAL

puts "Direction: #{dir}"
puts "Character: #{character}"
puts "Orientation: #{orientation}"


puts "\n\n.............Probando Dice............."

times = 1_000_000
promedios = Array.new(11,0.0)

# Probar Dice
for i in 1..times
  promedios[0] += Irrgarten::Dice.random_pos(10)/Float(times)
  promedios[1] += Irrgarten::Dice.who_starts(5)/Float(times)
  promedios[2] += Irrgarten::Dice.random_intelligence/Float(times)
  promedios[3] += Irrgarten::Dice.random_strength/Float(times)
  promedios[4] += (Irrgarten::Dice.resurrect_player ? 1 : 0)/Float(times)
  promedios[5] += Irrgarten::Dice.weapons_reward/Float(times)
  promedios[6] += Irrgarten::Dice.shields_reward/Float(times)
  promedios[7] += Irrgarten::Dice.health_reward/Float(times)
  promedios[8] += Irrgarten::Dice.weapon_power/Float(times)
  promedios[9] += Irrgarten::Dice.shield_power/Float(times)
  promedios[10] += Irrgarten::Dice.uses_left/Float(times)
end


puts "Promedios:"
puts "randomPos:            #{promedios[0]} ≈ 5"
puts "whoStarts:            #{promedios[1]} ≈ 2.5"
puts "randomIntelligence:   #{promedios[2]} ≈ 5"
puts "randomStrength:       #{promedios[3]} ≈ 5"
puts "resurrectPlayer:      #{promedios[4]} ≈ 0.3"
puts "weaponsReward:        #{promedios[5]} ≈ 1"
puts "shieldsReward:        #{promedios[6]} ≈ 1.5"
puts "healthReward:         #{promedios[7]} ≈ 2.5"
puts "weaponPower:          #{promedios[8]} ≈ 1.5"
puts "shieldPower:          #{promedios[9]} ≈ 1"
puts "usesLeft:             #{promedios[10]} ≈ 2.5"



